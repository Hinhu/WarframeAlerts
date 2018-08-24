# WarframeAlerts

This JavaFX app gets informations about events in Warframe from https://deathsnacks.com/wf/ and looks for info about event's prizes on http://warframe.wikia.com/wiki (during search, it sometimes uses https://www.ecosia.org/ search engine). Thanks to that user doesn't have to look for info about prize on his/her own.

I am not owner of sites mentioned above. I am grateful to their creators for making such contribution to Warframe fanbase. 
This project doesn't mean to exploit their work, it's just an app made by fellow Tenno to help players in identifying alerts' prizes.

# Usage

* Launching
  Download *WarframeTracker.jar* and *Config.txt* files. They should be together in the same directory..
  
  * Windows
  
    If you have Java installed on your system, just double click *WarframeTracker.jar* icon.

  * Linux
  
    Open terminal and go to the directory of *WarframeTracker.jar* file. Then use following command:
    ```bash
    java -jar WarframeTracker.jar
    ```
* Alerts screen

  You can use the scrollbar on the right of app's window. 

  If there is an image of the alerts prize, you can click on it to open http://warframe.wikia.com/wiki article about the prize on your default browser. In case the image is just a question mark, clicking it will send you to wiki's homepage.

  You can also refresh app manually by clicking "REFRESH" button on top of the screen. It may look like the app crashed for few seconds, but it should go back to normal state.

  If you want to personalize list of planets and nodes, in which you want your alerts to be tracked, just click the "CONFIG" button.

* Config screen

  You can check planets and nodes, which you unlocked.

  If you check a planet, its child nodes will also be checked and vice versa.

  You can also check (and uncheck) all planets using "Check All" check box.

  Save your choices by clicking "SAVE" button.

  Changes will affect alert screen when you refresh it.
