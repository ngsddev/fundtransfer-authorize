package com.erpconnect.controller;

import com.erpconnect.rsa.RsaUtils;
import org.springframework.web.bind.annotation.*;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping("/ft/v1")
public class RestControllerClass {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    @GetMapping("/hello")
    public String Hello() {
        return "Hello world";
    }

    @GetMapping("/generatekeypair")
    public String printKeyPair() throws Exception {
        KeyPair keyPair = RsaUtils.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(new FileOutputStream("C:/Users/BangMSI/Desktop/"
                    + "rsaPublicKey"));
            dos.write(publicKey.getEncoded());
            dos.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        try {
            dos = new DataOutputStream(new FileOutputStream("C:/Users/BangMSI/Desktop/"
                    + "rsaPrivateKey"));
            dos.write(privateKey.getEncoded());
            dos.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (dos != null)
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        return "Key generated";
    }

    @GetMapping("/getkey")
    public String GetKeyPair() throws Exception{
        PublicKey publicKey = RsaUtils.getPublicKey("C:/Users/BangMSI/Desktop/rsaPublicKey");

        return publicKey.toString();
    }

    @GetMapping("/encrypt")
    public String EncryptString() throws Exception{
        String string = "Password1";
        PublicKey publicKey = RsaUtils.getPublicKey("C:/Users/BangMSI/Desktop/rsaPublicKey");

        String cypherText = RsaUtils.encrypt(string, publicKey);
        return cypherText;
    }

    //LM+WJcaoOdFsnD/jGJK8cas92XSIEBrYHIyi6TnzUR7xRVnlcNYMIeDBMKaUAEbMnMY5Yz4t1Bybs6i3AaBm8I0CD1An5S+eza54/jTYczjqLR0yevEK+e/283NXHgZKUoFHBh7OV34CTYaUF2yNhYVjmbP5Vzi7i5lg9btMPuO3s41NO1UUd/UiENZreFOPOvV/82Yb7euFkXb9bvlY+rDSG98lfezfi/mrUukiC/A5Qvj+yRVrTk/FnLkx6I3yWqOLuOAaoGZ9ZesRfZCidME+34ISd7f6QoDzEgZ7Rg6D1D3p7lFONnrMDY51K4BJawNcwioOKeRKx/n2NtT/Zw==
    //LM+WJcaoOdFsnD/jGJK8cas92XSIEBrYHIyi6TnzUR7xRVnlcNYMIeDBMKaUAEbMnMY5Yz4t1Bybs6i3AaBm8I0CD1An5S+eza54/jTYczjqLR0yevEK+e/283NXHgZKUoFHBh7OV34CTYaUF2yNhYVjmbP5Vzi7i5lg9btMPuO3s41NO1UUd/UiENZreFOPOvV/82Yb7euFkXb9bvlY+rDSG98lfezfi/mrUukiC/A5Qvj+yRVrTk/FnLkx6I3yWqOLuOAaoGZ9ZesRfZCidME+34ISd7f6QoDzEgZ7Rg6D1D3p7lFONnrMDY51K4BJawNcwioOKeRKx/n2NtT/Zw==

    @PostMapping("/decrypt")
    public String DecryptString(@RequestBody Objects cypherText) throws Exception{
        String cypherText1 = "LM+WJcaoOdFsnD/jGJK8cas92XSIEBrYHIyi6TnzUR7xRVnlcNYMIeDBMKaUAEbMnMY5Yz4t1Bybs6i3AaBm8I0CD1An5S+eza54/jTYczjqLR0yevEK+e/283NXHgZKUoFHBh7OV34CTYaUF2yNhYVjmbP5Vzi7i5lg9btMPuO3s41NO1UUd/UiENZreFOPOvV/82Yb7euFkXb9bvlY+rDSG98lfezfi/mrUukiC/A5Qvj+yRVrTk/FnLkx6I3yWqOLuOAaoGZ9ZesRfZCidME+34ISd7f6QoDzEgZ7Rg6D1D3p7lFONnrMDY51K4BJawNcwioOKeRKx/n2NtT/Zw==";
        //cypherText = URLDecoder.decode(cypherText, "UTF-8");

        //PrivateKey privateKey = RsaUtils.getPrivateKey("C:/Users/BangMSI/Desktop/rsaPrivateKey");
        //String decryptedText = RsaUtils.decrypt(cypherText, privateKey);
        //return decryptedText;
//        if(cypherText.equals(cypherText1)){
//            return "True";
//        } else {
//            return "False";
//        }
        return cypherText.toString();
    }

    @PostMapping("/fundtransfer/transaction-inauth")
    public HashMap<String, String> FundTransferTransactInAuth (@RequestParam String debitAccoundId, String creditAccountId, String transactMessage) {
        HashMap<String, String> resultMap = new HashMap<>();
        resultMap.put("debit_account",debitAccoundId);
        resultMap.put("credit_account",creditAccountId);
        resultMap.put("transaction_date",dtf.format(now).toString());
        resultMap.put("message",transactMessage);
        return resultMap;
    }

    @PostMapping("/fundtransfer/transaction-inauth-remove")
    public HashMap<String, String> FundTransferTransactInAuthRemove (@RequestParam String debitAccoundId, String creditAccountId, String transactMessage) {
        HashMap<String, String> resultMap = new HashMap<>();
        resultMap.put("debit_account",debitAccoundId);
        resultMap.put("credit_account",creditAccountId);
        resultMap.put("transaction_date",dtf.format(now).toString());
        resultMap.put("message",transactMessage);
        return resultMap;
    }

    @PostMapping("/fundtransfer/transaction-auth")
    public HashMap<String, String> FundTransferTransactAuth (@RequestParam String debitAccoundId, String creditAccountId, String transactMessage) {
        HashMap<String, String> resultMap = new HashMap<>();
        resultMap.put("debit_account",debitAccoundId);
        resultMap.put("credit_account",creditAccountId);
        resultMap.put("transaction_date",dtf.format(now).toString());
        resultMap.put("message",transactMessage);
        return resultMap;
    }

}
