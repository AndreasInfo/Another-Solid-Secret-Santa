# Another-Solid-Secret-Santa

The goal was to write a timeless secret santa CLI with close to zero maintenance effort. It must include restrictions,
as this is something many secret santa apps are missing. This has a slightly negative impact on usability, but providing
the restrictions IS_SANTA_FOR and IS_NO_SANTA_FOR is as minimalistic and pure as it gets.

Technical speaking I chose chain of responsibility pattern to control the flow and strategy pattern for different
strategies. If you come up with a better or different algorithm, feel to add it.

### Build

Clone repo and run `gradlew clean build`.

### Usage

Run `java -jar another-solid-secret-santa-1.0-SNAPSHOT.jar` and provide app.properties in the directory where the
command is run including the following properties:

```
simplejavamail.smtp.host=smtp.bla.bli
simplejavamail.smtp.port=123
simplejavamail.smtp.username=blo
simplejavamail.smtp.password=blu
```

### Todo

- go one step back
