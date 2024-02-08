package com.leocalheiros.paymentsystem.dto;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PixChargeRequest {
    private final String chave;
    private final String valor;
    private final Map<String, Object> calendario;
    private final Map<String, Object> devedor;
    private final List<Map<String, Object>> infoAdicionais;

    public PixChargeRequest(String chave,
                            String valor,
                            Map<String, Object> calendario,
                            Map<String, Object> devedor,
                            List<Map<String, Object>> infoAdicionais) {
        this.chave = chave;
        this.valor = valor;
        this.calendario = calendario != null ? calendario : Map.of("expiracao", 3600);
        this.devedor = devedor;
        this.infoAdicionais = infoAdicionais;
    }

    // Getter methods

    public String getChave() {
        return chave;
    }

    public String getValor() {
        return valor;
    }

    public Map<String, Object> getCalendario() {
        return calendario;
    }

    public Map<String, Object> getDevedor() {
        return devedor;
    }

    public List<Map<String, Object>> getInfoAdicionais() {
        return infoAdicionais;
    }

    // Factory method for creating instances with default values

    public static PixChargeRequest createDefault(String chave, String valor) {
        Map<String, Object> calendario = Map.of("expiracao", 3600);
        return new PixChargeRequest(chave, valor, calendario, null, null);
    }
}

