package com.flowcode.langcab.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class LoginService {
    public void login() throws Exception {

        String s = "{\n" +
                "  \"type\": \"service_account\",\n" +
                "  \"project_id\": \"vocab-188617\",\n" +
                "  \"private_key_id\": \"124b223c4f90e693da92eb7f823d5c19a50ee8ac\",\n" +
                "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDVVf9IcSlK7ZGD\\ngNUR728teav+Wl5gu4kB2MPNSmRm3Qrxi6YceIOX6WXjJx0G4B0ZTCu9vrfy7FkT\\nPuVon8bfPsLFaJelHtN+o50rt0/dO+Mo50CbJYdibmzdz+1JBpGZtYpTLuJTWA0T\\nIZfOULhuadCm9Rgdm0iE90IiqFgybvsp1iHxLOahwZggfO1D5J/MdJG0tg7Vk8Ek\\nlbEZC2mi5wSvMYmfl5HAjDVHslwFfh0V041wI9risTZrdgZUb4y+MMSXHz4p+MwM\\nlRlQiwYJUYTtOH6OfgXbyVhWGe3vnLkBK+qz+aAfrKdfsA7XX9ZQHwf72J/dhoA+\\neDLpKOb3AgMBAAECggEAGrPP5zZaz8kanJYklUEd7lg7YfPXuk3e4T9LiseT95gV\\nRZSQLyIafoo1OZBsvq6QldkF5JMZnSteHt2Rg130W38Ui/cCfCV2nXRHOPCR64QS\\nc802NfUlk/l0YPMkpS5QTSf+71h/9aSgxlM/GBDcdN2Dw1IMObB1DEeSnJnrqCwn\\ne8WtkvwEhjoi55H+HO9rKRVKHz4dxsoMIidurJui0Cqt8SMX1s4qJ896o5yCiDd2\\nnkkjr8VnI3d7v3WqJhfoG+JcQdDQ92f/TnZMKYV+jq4K3/OYJEtRw74pcHdbVsLp\\nSmphKZonwO4uHKkepVZoCJmVdVTItW/P7/weMHglAQKBgQDs9smC1eOQtTdoXAWQ\\nxVg7YRSJNQ6hcmfah7lozJEhOh1X5lt7orwOc8p1BbnK2hYZlD9A7iX1539VAT2L\\nlzegxUdZfiO48gTaJvajDX5E1DfOuJ+La1jlcl0qy7oOmSR+kmpInpm8Y7nncw3u\\nzAEQgvdF08533B4p+OAJ/bmegQKBgQDmeUsu1JTn4GkSGQMe/ETsOK3cGqg89JNT\\nrcHyuZJMyNy1X1oIz3MXggJect6Q9aqF9r32aE4CKjS+tprsCDWzHiDe58PaGP7g\\nb/IUhhh0jg5VrdOGQXcDysNOIiVVBRI1FfrJz909zE2oVgQL7qA8VFxXnVfk9q/a\\nsbKOc9W5dwKBgAiz+6yxfF0pOrK7hnzNsrLjmuR7LWFimUveZkHiGYZ14vQCguOk\\nk3GYcRwhLLS1k9A2sKgK1xwT94pAtGj3EdT2yesQhfFZ+jGk8rQEX9nZTX9rKnjt\\nZWNv8toKh3fXyg5YBE1Mfer8YqyMfjuCBJv9o2VOJSwjynvSJzBWwEWBAoGAeHGA\\n2dtcagU7w6pwmBvLxQslyLXhn+FMsNlIVxmlnB0IQnyvJ5WKFwnpRQSQzX6k+9dj\\nlMcTpUv7PoNdWOY9cfrXwMWr82lGoXG/N/yQATFPynTJ6wqBMOkgwc9UZSb+SeHP\\nqvHHNAl1t/gjqzysXnrTuzHOAijs48QjhRCB66cCgYEA6Yq7HHqWCvh4hKeagEjj\\nCnNt0jj74nyOTyY+dH+S9P4CDuCMW88RV/Z0EbzdA+E+s1+nkndsE99krusVVKDf\\n2LrDqnHx4l4k7aV+td9/o1RqQK9R6NCIbglVTgvSbZkVW3GJf9kjpcL+YTytZyum\\nj5myW6GT0H4tj0W5xB3ahIg=\\n-----END PRIVATE KEY-----\\n\",\n" +
                "  \"client_email\": \"firebase-adminsdk-fgat5@vocab-188617.iam.gserviceaccount.com\",\n" +
                "  \"client_id\": \"112367262917863216148\",\n" +
                "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                "  \"token_uri\": \"https://accounts.google.com/o/oauth2/token\",\n" +
                "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-fgat5%40vocab-188617.iam.gserviceaccount.com\"\n" +
                "}";
        InputStream stream = new ByteArrayInputStream(s.getBytes());

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(stream))
                .build();

        FirebaseApp.initializeApp(options);
    }
}
