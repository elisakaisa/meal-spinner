# meal-spinner

## Description
Mobile application, for android, in Java, with a slot-machine type spinner to create meal ideas based on (currently hardcoded, [v1.0.0](https://github.com/elisakaisa/meal-spinner/releases/tag/1.0.0), but later to be set by the user) foods, separated into proteins, carbs, and greens.

The protein list, or the first wheel to be spinned, also contains so called "jackpot meals", which are for instance pizza, or tacos, that are not exactly composed by the 3 food groups. After each spinning of the wheel, the likelihood of getting a specific food is updated, in order to reduce the chances of having multiple times the same meal in a row. This is a way to control the randomness.

The spinned meals are stored locally, and the user can store a maximum of 5 meals (currently hardcoded, [v1.0.0](https://github.com/elisakaisa/meal-spinner/releases/tag/1.0.0), but later to be set by the user), and can delete them.

<p>
  <img src="https://user-images.githubusercontent.com/79315440/205643844-2b34fffe-99fa-4f3f-91ea-09c43763ca4f.jpg" width="300" />
</p>

Screenshot from [v1.0.0](https://github.com/elisakaisa/meal-spinner/releases/tag/1.0.0).

## Releases
- [v1.0.0](https://github.com/elisakaisa/meal-spinner/releases/tag/1.0.0)

## Installation
The easiest way to run the application is to have Android studio installed and run the application either on the emulator or on a mobile phone with developer options activated.

Then, run the foillowing command in the terminal:

```bash
git clone https://github.com/elisakaisa/meal-spinner.git
```

and run gradle sync.

## Implementation
The app has a very simple structure, with one fragment haviong the slot-machine type of spinning wheels and the recyclerView in which the spinned meals are displayed. It follows a simple MVVM pattern. The spinned meals are stored into a local room database. A viewModel handles the display of the information on the view. The logic of the spinner is contained in the `Wheel.java` class.

## Known issues and improvements

Known issues in [v1.0.0](https://github.com/elisakaisa/meal-spinner/releases/tag/1.0.0):
- none

Known issues:
- none

To be improved:
- settings page where the user can set how long the wheels spins, and how many meals can be saved
- the user can add foods themselves, and set whether those foods are visible in the spinner at the moment (like an on/off switch)
- ...

## Authors

Elisa Perini [github](https://github.com/elisakaisa) | [linkedIn](https://www.linkedin.com/in/elisa-perini-2759ba227/)
