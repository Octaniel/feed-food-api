package com.dro.feedfood.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * User: Octaniel
 * Date: 21/09/2020
 * Time: 18:00
 */
@Entity
@Table(name = "gosto")
@Getter
@Setter
@EqualsAndHashCode
public class Gosto {

    @EmbeddedId
    private IdGosto idGosto;
}
