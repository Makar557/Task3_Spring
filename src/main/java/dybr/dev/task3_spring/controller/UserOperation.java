package dybr.dev.task3_spring.controller;

import dybr.dev.task3_spring.dto.UserResponseDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import java.util.Arrays;
import java.util.List;

public enum UserOperation {

    CREATE(new LinkType[]{LinkType.SELF, LinkType.UPDATE, LinkType.DELETE, LinkType.USERS}),

    FIND_BY_ID(new LinkType[]{LinkType.SELF, LinkType.UPDATE, LinkType.DELETE, LinkType.USERS}),

    FIND_ALL(new LinkType[]{LinkType.SELF, LinkType.UPDATE, LinkType.DELETE}),

    DELETE(new LinkType[]{LinkType.USERS}),

    UPDATE(new LinkType[]{LinkType.SELF, LinkType.DELETE, LinkType.USERS});

    private final LinkType[] links;

    UserOperation(LinkType[] links) {
        this.links = links;
    }

    private EntityModel<UserResponseDTO> generateEntityModel(UserResponseDTO user) {

        Link[] linksForEntityModel = Arrays.stream(links).map(link -> link.getLink(user)).toArray(Link[]::new);

        return EntityModel.of(user, linksForEntityModel);
    }

    private CollectionModel<EntityModel<UserResponseDTO>> generateCollectionModel(List<UserResponseDTO> users) {

        List<EntityModel<UserResponseDTO>> models = users.stream().map(this::generateEntityModel).toList();

        return CollectionModel.of(models, LinkType.getUsersLink());
    }

    public EntityModel<UserResponseDTO> generate(UserResponseDTO user) {
        return generateEntityModel(user);
    }

    public CollectionModel<EntityModel<UserResponseDTO>> generate(List<UserResponseDTO> users) {
        return generateCollectionModel(users);
    }
}