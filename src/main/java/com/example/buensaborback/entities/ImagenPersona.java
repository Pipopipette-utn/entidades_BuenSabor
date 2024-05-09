package com.example.buensaborback.entities;

import jakarta.persistence.Entity;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Builder
public class ImagenPersona extends Base{

    @Builder.Default
    private String url = "https://cdn.icon-icons.com/icons2/2483/PNG/512/user_icon_149851.png";

    public ImagenPersona(String url){
        this.url = url;
    }
    
}
