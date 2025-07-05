# NonUpdate-Modern
Unwanted connection blocking for modern Minecraft running on Java 17+

## Background
Some players may have a poor network connection, and their packs sometimes stuck in the middle of launching
The mod is made for this: Just add the class names to config and laggy connections will be blocked.

## FAQ
- Q: How do I know it was network connection slowing down my game?
- A: Use visualvm, spark or whatever profiler to check what's running
- Q: How should I add class to config?
- A: Add the internal name (separate by `/`) of class to `targets` in `nonupdate-modern.json` in `config/`
- Q: How do I know if it worked?
- A: Set `debugMode` to true and check the log. It will log all connection attempts now.
