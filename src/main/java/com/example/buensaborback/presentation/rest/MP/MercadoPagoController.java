package com.example.buensaborback.presentation.rest.MP;

import com.example.buensaborback.domain.entities.Pedido;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path= "/mercadoPago")
public class MercadoPagoController {

    @PostMapping("/preference_mp")
    public PreferenceMp getPreferenciaIdMercadoPago(@RequestBody Pedido pedido) throws MPException {
        try {
            MercadoPagoConfig.setAccessToken("TEST-650691690367918-061007-f316ba9ff0123908c8e3d360518f9896-174713307");
            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .id(String.valueOf(pedido.getId()))
                    .title("El Buen Sabor")
                    .description("Pedido realizado desde el carrito de compras")
                    .pictureUrl("https://static.vecteezy.com/system/resources/previews/007/126/404/non_2x/food-delivery-icon-free-vector.jpg")
                    .quantity(1)
                    .currencyId("ARG")
                    .unitPrice(new BigDecimal(pedido.getTotal()))
                    .build();
            List<PreferenceItemRequest> items = new ArrayList<>();
            items.add(itemRequest);

            PreferenceBackUrlsRequest backURL = PreferenceBackUrlsRequest.builder().success("http://localhost:5173/menu")
                    .pending("http://localhost:5173/menu").failure("http://localhost:5173/menu").build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .backUrls(backURL)
                    .build();
            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);

            PreferenceMp mpPreference = new PreferenceMp();
            mpPreference.setStatusCode(preference.getResponse().getStatusCode());
            mpPreference.setId(preference.getId());
            return mpPreference;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
