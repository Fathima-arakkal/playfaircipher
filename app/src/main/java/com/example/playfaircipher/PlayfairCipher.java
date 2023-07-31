package com.example.playfaircipher;

public class PlayfairCipher {

    private final String key;
    private char[][] keyMatrix;

    public PlayfairCipher(String key) {
        this.key = key;
        generateKeyMatrix();
    }

    private void generateKeyMatrix() {
        // Prepare key matrix
        // Implement your own logic here to generate the key matrix
        // This example uses a simple method of converting the key to uppercase and removing duplicate letters
        String sanitizedKey = key.toUpperCase().replaceAll("[^A-Z]", "");
        String alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ"; // Exclude letter J

        // Combine sanitized key and remaining alphabet
        String combinedKey = sanitizedKey + alphabet;

        // Create the key matrix (5x5 grid)
        keyMatrix = new char[5][5];
        int index = 0;

        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                keyMatrix[row][col] = combinedKey.charAt(index);
                index++;
            }
        }
    }

    public String encrypt(String plaintext) {
        // Implement the encryption logic here
        // This example uses a simple implementation of the Playfair cipher

        StringBuilder ciphertext = new StringBuilder();

        // Prepare plaintext by removing non-alphabetic characters and converting to uppercase
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");

        // Divide the plaintext into pairs of letters (digraphs)
        String[] digraphs = getDigraphs(plaintext);

        // Apply the encryption rules to each digraph
        for (String digraph : digraphs) {
            char char1 = digraph.charAt(0);
            char char2 = digraph.charAt(1);

            int row1 = -1, col1 = -1, row2 = -1, col2 = -1;

            // Find the positions of the characters in the key matrix
            for (int row = 0; row < 5; row++) {
                for (int col = 0; col < 5; col++) {
                    if (keyMatrix[row][col] == char1) {
                        row1 = row;
                        col1 = col;
                    } else if (keyMatrix[row][col] == char2) {
                        row2 = row;
                        col2 = col;
                    }
                }
            }

            char encryptedChar1, encryptedChar2;

            // Apply the encryption rules
            if (row1 == row2) { // Same row
                encryptedChar1 = keyMatrix[row1][(col1 + 1) % 5];
                encryptedChar2 = keyMatrix[row2][(col2 + 1) % 5];
            } else if (col1 == col2) { // Same column
                encryptedChar1 = keyMatrix[(row1 + 1) % 5][col1];
                encryptedChar2 = keyMatrix[(row2 + 1) % 5][col2];
            } else { // Form a rectangle
                encryptedChar1 = keyMatrix[row1][col2];
                encryptedChar2 = keyMatrix[row2][col1];
            }

            // Append the encrypted characters to the ciphertext
            ciphertext.append(encryptedChar1);
            ciphertext.append(encryptedChar2);
        }

        return ciphertext.toString();
    }

    public String decrypt(String ciphertext) {
        // Implement the decryption logic here
        // This example uses a simple implementation of the Playfair cipher

        StringBuilder plaintext = new StringBuilder();

        // Divide the ciphertext into pairs of letters (digraphs)
        String[] digraphs = getDigraphs(ciphertext);

        // Apply the decryption rules to each digraph
        for (String digraph : digraphs) {
            char char1 = digraph.charAt(0);
            char char2 = digraph.charAt(1);

            int row1 = -1, col1 = -1, row2 = -1, col2 = -1;

            // Find the positions of the characters in the key matrix
            for (int row = 0; row < 5; row++) {
                for (int col = 0; col < 5; col++) {
                    if (keyMatrix[row][col] == char1) {
                        row1 = row;
                        col1 = col;
                    } else if (keyMatrix[row][col] == char2) {
                        row2 = row;
                        col2 = col;
                    }
                }
            }

            char decryptedChar1, decryptedChar2;

            // Apply the decryption rules
            if (row1 == row2) { // Same row
                decryptedChar1 = keyMatrix[row1][(col1 + 4) % 5];
                decryptedChar2 = keyMatrix[row2][(col2 + 4) % 5];
            } else if (col1 == col2) { // Same column
                decryptedChar1 = keyMatrix[(row1 + 4) % 5][col1];
                decryptedChar2 = keyMatrix[(row2 + 4) % 5][col2];
            } else { // Form a rectangle
                decryptedChar1 = keyMatrix[row1][col2];
                decryptedChar2 = keyMatrix[row2][col1];
            }

            // Append the decrypted characters to the plaintext
            plaintext.append(decryptedChar1);
            plaintext.append(decryptedChar2);
        }

        return plaintext.toString();
    }

    private String[] getDigraphs(String text) {
        // Split the text into pairs of letters (digraphs)
        int length = text.length();
        int count = (int) Math.ceil(length / 2.0);
        String[] digraphs = new String[count];
        int index = 0;

        for (int i = 0; i < length; i += 2) {
            if (i + 1 < length) {
                digraphs[index] = text.substring(i, i + 2);
            } else {
                digraphs[index] = text.substring(i) + "X";
            }
            index++;
        }

        return digraphs;
    }
}