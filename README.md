# WEC_Cryptography

### Base64 encoding
Base64 is an encoding scheme used to represent binary data in a printable ASCII format by translating it into a radix-64 representation. Each of the 6-bit binary sequences from 0 to 63 are assigned a base64 alphabet. There is also an extra 65th character '=' which is used for padding. This mapping between the 6-bit binary sequence and the corresponding base64 alphabet is used during the encoding process.


### Caesar cipher
The caesar cipher or caesar shift, named after Roman dictator Julius Caesar, is one of the earliest, simplest and most widely known encryption technique. It is a type of substitution cipher in which each letter of the given text is replaced by a letter some fixed positions down the alphabet. For example, for a caesar shift of 2, A would be replaced by C, B would be replaced by D, and so on.


### Playfair cipher
Playfair cipher is the first literal digram substitution cipher. It encrypts each digram (a pair of letters) instead of a single alphabet.   
The cipher consists of a key square which is a 5x5 grid of alphabets that is the key for encrypting the plaintext. As the key square can only hold 25 alphabets, one letter is usually omitted from the table (J was omitted in the given problem). It is a symmetric cipher and hence same key is used for both encryption and decryption. The plaintext is split into pairs of two letters and are encrypted using the following rules:
1. If both letters of the digram are in same row, they are replaced by letters to their immediate right in the key square.
2. If both letters of the digram are in same column, they are replaced by the letter below them in the key square.
3. If the letters are not in same row or column, they are replaced by the other two corners of the rectangle formed by them.


### RSA Encryption
RSA is a public key encryption algorithm that uses an asymmetric encryption algorithm to encrypt data. It works on two different keys, public key and private key used for encryption and decryption respectively.   
Public key consists of two whole numbers n and e. The message to be encrypted is converted to a number m and the ciphertext c is computed such that,  
c = m<sup>e</sup> mod n  
The ciphertext can be decrypted using the private key d such that,  
m = c<sup>d</sup> mod n
