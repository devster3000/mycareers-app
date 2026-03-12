# MyCareers app
A very simple android application.

### Screenshots
<div>
  <img src="https://github.com/devster3000/Assets/blob/main/mycareers/images/screenshots/screenshot_1.PNG" width="30%" />
  <img src="https://github.com/devster3000/Assets/blob/main/mycareers/images/screenshots/screenshot_2.PNG" width="30%" />
  <img src="https://github.com/devster3000/Assets/blob/main/mycareers/images/screenshots/screenshot_3.PNG" width="30%" />
</div>

### How it was made
- Kotlin w/ Android Studio
- Jetpack Compose

### How it works
- 6 Courses. Each course has 4 modules, some less. Currently, each module uses a WebView container to lead the user to the corresponding link on the MyCareers website.
- The user can then complete the module in the website, and a "isCompleted" variable is assigned to that module. This then awards the appropriate badge, shows the completion state visually and adds the course to the "Continue" section on the home screen.
- Once all modules inside of a course are completed, the course is assigned the "isCompleted" variable and is removed from the homescreen Continue section.

### What's next
- Integrated content - rather than just using a WebView container, scrape the content from the website and inject it into the app.
- Revamped UI utilizing M3 Expressive.

