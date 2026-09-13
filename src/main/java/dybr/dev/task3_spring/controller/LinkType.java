package dybr.dev.task3_spring.controller;

import dybr.dev.task3_spring.dto.UserResponseDTO;
import org.springframework.hateoas.Link;
import java.util.function.Function;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public enum LinkType {

    SELF((user) -> linkTo(methodOn(UserController.class).findById(user.getId())).withSelfRel()),

    UPDATE(user -> linkTo(methodOn(UserController.class).update(null, user.getId())).withRel("update")),

    DELETE((user) -> linkTo(methodOn(UserController.class).deleteById(user.getId())).withRel("delete")),

    USERS((ignored) -> getUsersLink());

    private final Function<UserResponseDTO, Link> function;

    LinkType(Function<UserResponseDTO, Link> function) {
        this.function = function;
    }

    public static Link getUsersLink() {
        return linkTo(methodOn(UserController.class).findAll()).withRel("users");
    }

    public Link getLink(UserResponseDTO user) {
        return function.apply(user);
    }
}
