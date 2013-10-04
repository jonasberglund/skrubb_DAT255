#Suggest GUI Test

##Steps

1. Start the application and tap the H-icon in the left upper corner of the screen.
2. Click on "Felanmälan" in the menu.
3. A new page should load. Click on the textField saying "Skriv här" to bring up the keyboard.
4. Type a message using the keyboard. When finnished typing, press the device back-button wich should close the keyboard.
5. Skip this step untill told to return. Click on the ceckbox and make sure it´s checked.
6. Press the "Skicka"-button (if no button is visible, scroll down untill the button is found).
7. Select the prefered E-mailclient in the popup. If no clients are found, a toast (popup) will come up and the message will not be possible to send. 
8. The selected client should start and have all fields filled in by the application and be ready for sending. The receiver should be 8800@chalmers.se unless step 5 was followed, in that case the receiver should be support@chalmers.se.
9. Only do this step one time. Close the E-mailclient and go back to step 4 (step 1 if unable to return to the application) but this time do step 5 aswell.
10. This step is to test that the E-mail is sent correctly, skip this step otherwise. Edit the recceiver field in the client to an address you have access to. Send the message. The client will close and the "Felanmälan"-page will reload and be ready for a new message. Check that the message was recceived correctly.

If all steps above succeed, the test was a success.