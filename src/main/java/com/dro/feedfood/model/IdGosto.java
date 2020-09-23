package com.dro.feedfood.model;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * User: Octaniel
 * Date: 21/09/2020
 * Time: 18:05
 */
@Embeddable
public class IdGosto implements Serializable {
    @NotNull(message = "video não deve ser null")
    @JoinColumn(name = "id_video")
    @OneToOne
    private Video video;

    @NotNull(message = "pessoa não deve ser null")
    @JoinColumn(name = "id_pessoa")
    @OneToOne
    private Pessoa pessoa;
}
