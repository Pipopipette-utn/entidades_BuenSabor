package com.example.buensaborback.entities;

import com.example.buensaborback.entities.enums.FormaPago;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Builder
public class Factura extends Base{

    @NotBlank(message = "La fechaFacturacion es requerida")
    private LocalDate fechaFacturacion;
    private Integer mpPaymentId;
    private Integer mpMerchantOrderId;
    private String mpPreferenceId;
    private String mpPaymentType;

    // La forma de pago debería ser igual que la formaPago en pedido
    private FormaPago formaPago;
    private Double totalVenta;


}
