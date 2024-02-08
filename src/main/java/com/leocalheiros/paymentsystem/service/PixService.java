package com.leocalheiros.paymentsystem.service;

import br.com.efi.efisdk.EfiPay;
import br.com.efi.efisdk.exceptions.EfiPayException;
import com.leocalheiros.paymentsystem.dto.PixChargeRequest;
import com.leocalheiros.paymentsystem.exceptions.*;
import com.leocalheiros.paymentsystem.pix.Credentials;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class PixService {

    @Value("${CLIENT_ID}")
    private String clientId;

    @Value("${CLIENT_SECRET}")
    private String clientSecret;

    public JSONObject pixCreateEVP() {
        JSONObject options = configuringJsonObject();

        try {
            EfiPay efi = new EfiPay(options);
            JSONObject response = efi.call("pixCreateEvp", new HashMap<>(), new JSONObject());
            return response;
        } catch (EfiPayException e) {
            throw new PixMaxEVPException("Número máximo de chaves PIX excedida!");
        } catch (Exception e) {
            throw new PixEVPGenericException(e.getMessage());
        }

    }

    public JSONObject pixCreateCharge(PixChargeRequest pixChargeRequest) {
        JSONObject options = configuringJsonObject();

        JSONObject body = new JSONObject();
        body.put("calendario", pixChargeRequest.getCalendario());
        body.put("devedor", pixChargeRequest.getDevedor());
        body.put("valor", new JSONObject().put("original", pixChargeRequest.getValor()));
        body.put("chave", pixChargeRequest.getChave());
        body.put("infoAdicionais", pixChargeRequest.getInfoAdicionais());

        try {
            EfiPay efi = new EfiPay(options);
            JSONObject response = efi.call("pixCreateImmediateCharge", new HashMap<>(), body);

            int idFromJson = response.getJSONObject("loc").getInt("id");
            Map<String, Object> qrCodeInfo = pixGenerateQRCode(String.valueOf(idFromJson));

            // Adiciona as informações do QR Code à resposta final
            response.put("qrCodeImageFile", qrCodeInfo.get("qrCodeImageFile"));
            response.put("linkVisualizacao", qrCodeInfo.get("linkVisualizacao"));

            return response;
        } catch (EfiPayException e) {
            throw new PixFieldsException(e.getMessage());
        } catch (Exception e) {
            throw new PixGenericException(e.getMessage());
        }
    }

    private Map<String, Object> pixGenerateQRCode(String id) {
        JSONObject options = configuringJsonObject();
        Map<String, Object> qrCodeInfo = new HashMap<>();

        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);

        try {
            EfiPay efi = new EfiPay(options);
            Map<String, Object> response = efi.call("pixGenerateQRCode", params, new HashMap<>());

            // Cria uma pasta para armazenar os QR codes se ela ainda não existir
            File folder = new File("qrCodes");
            if (!folder.exists()) {
                folder.mkdir();
            }

            String qrCodeFileName = "qrCode_" + id + ".png";

            // Salva a imagem do QR Code em um arquivo dentro da pasta
            File outputfile = new File(folder, qrCodeFileName);
            ImageIO.write(ImageIO.read(new ByteArrayInputStream(
                    javax.xml.bind.DatatypeConverter.parseBase64Binary(
                            ((String) response.get("imagemQrcode"))
                                    .split(",")[1]))), "png", outputfile);


            qrCodeInfo.put("qrCodeImageFile", outputfile.getAbsolutePath());
            qrCodeInfo.put("linkVisualizacao", response.get("linkVisualizacao"));

            return qrCodeInfo;
        } catch (EfiPayException e) {
            System.out.println("Erro ao gerar QR Code: " + e.getMessage());
            throw new PixQRCodeLocationException("Location não encontrada!");
        } catch (Exception e) {
            System.out.println("Erro genérico ao gerar QR Code: " + e.getMessage());
            throw new PixGenericException("Erro ao gerar QR Code: " + e.getMessage());
        }
    }

    private JSONObject configuringJsonObject() {
        Credentials credentials = new Credentials();

        JSONObject options = new JSONObject();
        options.put("client_id", clientId);
        options.put("client_secret", clientSecret);
        options.put("certificate", credentials.getCertificate());
        options.put("sandbox", credentials.isSandbox());

        return options;
    }
}
