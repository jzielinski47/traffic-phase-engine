# Traffic Phase Engine
#### Simulation
This is a CLI-based simulation of an intelligent traffic intersection. The app takes an input.json file with a sequence of traffic commands and produces an output.json that reflects the system's decisions.

#### Background
Some time ago, I applied for an internship and received a recruitment task to simulate an intelligent traffic intersection. I managed to complete a fullstack app using TypeScript, React, and Node. I delivered it on time, but it did not meet the expectations of the recruitment team.
Despite that, I got a detailed code review and a lot of constructive feedback, which I really appreciated. After it was all over, I decided to rebuild the whole thing from scratch. This time, I wanted to focus on clean logic, proper arbitration, and safe traffic routing. No shortcuts. Just a pure CLI simulation written in Java.

The original project with frontend, REST API, and emergency vehicle handling is here: [link](https://github.com/jzielinski47/intelligent-traffic-control).

#### Tech stack
- Java 21 LTS
- Gradle using Kotlin DSL

#### Algorithm
Unlike in the TypeScript version I created before, this time I developed a complete algorithm that dynamically assigns priority to vehicles using a service that resolves conflicting and nonconflicting routes. Priority at the intersection is based on the timestamp, meaning the longest waiting vehicle goes first. Once the algorithm selects a vehicle, it gives it a green light and opens all nonconflicting routes related to it. At every step, it makes sure that any newly opened route does not collide with the ones that were already opened.

There is no predefined list of route groups. All conflicts are checked dynamically.

#### How it works
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

#### What is currently implemented
- CLI simulation engine
- JSON input and output
- Dynamic arbitration based on waiting time
- Full runtime conflict checking
- No hardcoded route patterns
- Clean and testable design

Jakub Zieliński 2025
