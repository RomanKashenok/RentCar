package com.kashenok.rentcar.entity;

import java.io.Serializable;

/**
 *
 * Class Entity - base entity for all entities in system
 */
public abstract class Entity implements Serializable{

    private static final long serialVersionUID = 3022398792640451496L;

    long id;

    public Entity() {
    }

        @Override
    public String toString() {
        return "Entity {" + "id=" + id + '}';
    }

}
