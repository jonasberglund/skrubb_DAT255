#Suggest GUI Test

##Steps

1. Start the application and tap the H-icon in the left upper corner of the screen.
2. Click on "Föreslå" in the menu.
3. A new page should load and click on the textField saying "Skriv här" to bring up the keyboard.
4. Type a message using the keyboard. When finnished typing, press the device back-button wich should close the keyboard.
5. Press the "Skicka"-button (if no button is visible, scroll down untill the button is found).
6. Select the prefered E-mailclient in the popup. If no clients are found, a toast (popup) will come up and the message will not be possible to send. 
7. The selected client should start and have all fields filled in by the application and be ready for sending.
8. This step is to test that the E-mail is sent correctly, skip this step otherwise: Edit the recceiver field in the client to an address you have access to.
9. Send the message. The client will close and the "Förslå"-page will reload and be ready for a new message.
10. If step 8 was used, check that the message was recceived correctly.

If all steps above succeed, the test was a success.
