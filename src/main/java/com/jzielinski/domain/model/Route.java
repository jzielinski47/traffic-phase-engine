package com.jzielinski.domain.model;

import com.jzielinski.enums.Direction;

import java.util.Map;

public record Route(Direction origin, Direction destination) {}
