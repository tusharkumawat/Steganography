# Steganography
Steganography is the practice of concealing data within another data.Steganography is different from cryptography. While cryptography means transformation of data in order to hide it's information content to prevent it's unauthorized use, steganography means hiding of data within an image, audio, video or any other file.
Essentially the difference is that, while both hide a message, steganography is meant to make the message invisible, while cryptography changes the message’s form, by means of replacement and/or algorithm.
This code is written in java and involves hiding of data into an image file using least significant bit (LSB) insertion technique. 
In this method, we can take the binary representation of the hidden_data and overwrite the LSB of each byte within the cover_image. If we are using 24-bit color, the amount of change will be minimal and indiscernible to the human eye. As an example, suppose that we have three adjacent pixels (nine bytes) with the following RGB encoding:

                                              10010101   00001101   11001001
                                              10010110   00001111   11001010
                                              10011111   00010000   11001011
Now suppose we want to "hide" the following 9 bits of data (the hidden data is usually compressed prior to being hidden): 101101101. If we overlay these 9 bits over the LSB of the 9 bytes above, we get the following (where bits in bold have been changed):

                                              10010101   00001100   11001001
                                              10010111   00001110   11001011
                                              10011111   00010000   11001011
Note that we have successfully hidden 9 bits but at a cost of only changing 4, or roughly 50%, of the LSBs.
