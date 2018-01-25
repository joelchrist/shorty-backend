package nl.joelchrist.shorty.util;

import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ShortyUtil {

    public String generateShortIdentifier() {
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator
                .Builder()
                .withinRange('a', 'z')
                .build();
        return randomStringGenerator.generate(5);
    }

}
