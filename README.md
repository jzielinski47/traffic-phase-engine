# Traffic Phase Engine
This is a CLI-based simulation of an intelligent traffic intersection. The app takes an input.json file with a sequence of traffic commands and produces an output.json that reflects the system's decisions.

## Background
Some time ago, I applied for an internship and received a recruitment task to simulate an intelligent traffic intersection. I managed to complete a fullstack app using TypeScript, React, and NodeJS. I delivered it on time, but it did not meet the expectations of the recruitment team.
Despite that, I received a detailed code review with constructive feedback, which I genuinely appreciated. I took the time to analyze what went wrong and what could be improved. This project was carefully redesigned from scratch with a clear focus on scalability, maintainability, and performance. Instead of incrementing a counter at each step, I use timestamps to track vehicle wait times more efficiently. I also introduced internal flags to avoid unnecessary iterations and redundant checks. I am aware there may still be room for further optimization, but I believe that is part of the process of learning from mistakes and improving over time.

My previous fullstack project referenced here: [Intelligent Traffic Control](https://github.com/jzielinski47/intelligent-traffic-control).

## Features
- CLI simulation engine
- JSON input and output
- Dynamic arbitration based on waiting time
- Emergency vehicles preemption
- Full runtime conflict checking- 
- No hardcoded route patterns
- Clean and scalable design

## Tech stack
- Java 21 LTS
- Gradle using Kotlin DSL

## Author

- [@jzielinski47](https://www.github.com/jzielinski47)

## Algorithm
Unlike in the TypeScript version I created before, this time I developed a complete algorithm that dynamically assigns priority to vehicles using a service that resolves conflicting and non-conflicting routes. Priority at the intersection is based on the timestamp, meaning the vehicle with the longest wait time goes first. Once selected, the vehicle is granted a green light, and all non-conflicting routes associated with it are opened. At each decision point, the system ensures that no new route conflicts with those already opened.
There is no predefined list of route groups as it was previously resolved. All potential conflicts are checked dynamically. The algorithm was carefully designed before implementation.

#### Execution flow
1. The application is launched with two arguments: `<input.json>` and `<output.json>`, provided as relative paths.
2. File operations are handled by the `FileController`, which encapsulates all logic related to reading and writing to files.
3. The input command list is parsed based on DTOs that define supported `CommandTypes`.
4. A `SimulationEngine` instance is initialized using the parsed commands.
5. The simulation is executed by invoking the engine.

#### Under the hood of the Simulation Engine
1. The engine iterates through each command in the sequence.
2. Each command is validated to ensure that all required fields are present and correctly defined.
3. A centralized `CommandHandler` routes the validated commands to their respective handlers:
    - AddVehicle Handler registers vehicles within the simulation context by placing them on valid roads.<br />If the vehicle is flagged as an emergency vehicle, the corresponding road is marked for optimized lookup during prioritization.
    - Step Handler advances the simulation by one phase.<br />
        1. The priority is being resolved based on the front row's vehicles' timestamps.
            - If an emergency vehicle is present (identified via a pre-flagged road), it is given top priority.<br />This marks a significant improvement over the previous version, as the algorithm now performs a direct lookup of the flagged road rather than scanning all roads individually.
            - If not, the vehicle with the earliest timestamp is selected.
        2. The RouteService evaluates all potential non-conflicting routes based on the selected priority vehicle. It ensures that newly considered routes do not conflict with any already opened.
        3. Vehicles on routes granted a green light are allowed to exit the intersection.
        4. All other traffic is halted with a red light, and the simulation enters a waiting state until the next step command is issued.
4. After the simulation completes, each step recorded in the `SimulationContext` is written to the specified `output.json` file.
5. The simulation is now complete

By implementing the logic using an object-oriented approach and delegating responsibilities across well-defined, single-responsibility classes, the system becomes significantly more efficient, maintainable, and scalable. It's a major improvement over the previous TypeScript version. Now, having all the logic encapsulated makes it much easier to read, maintain, and browse through. It can be easily deployed later in other forms than CLI. Much safer to do anything here as I don't directly rely on a single object. SimulationContext is a singleton used once and referenced via carefully distributed methods.

### Usage/Examples
You provide an `input.json` file with commands such as `addVehicle` and `step`. Each step runs one simulation phase where the system processes all queues and determines which vehicles can safely leave the intersection.
The result is saved into `output.json` with a list of vehicles that left during each step.

Sample input
```json
{
    "commands": [
        {
            "type": "addVehicle",

            "vehicleId": "vehicle1",

            "startRoad": "north",

            "endRoad": "east"
        },
        {
            "type": "addVehicle",

            "vehicleId": "vehicle2",

            "startRoad": "south",

            "endRoad": "west"
        },
        {
            "type": "step"
        },
        {
            "type": "step"
        }
    ]
}
```

Expected output:
```json
{
    "stepStatuses": [
        {
            "leftVehicles": [
                "vehicle1", 
                "vehicle2"
            ]
        },
        {
            "leftVehicles": [

            ]
        }
    ]
}
```


