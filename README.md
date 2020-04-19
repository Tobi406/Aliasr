# Aliasr

Aliasr is a plugin for the [Velocity](https://github.com/VelocityPowered/Velocity) minecraft server proxy.

It can add configurable aliases using RegEx.

### Commands
Aliasr itself has one command: `/aliasr`

No permission is required for it to run, but if the executor has the `aliasr.reload` permission, the config (and so all aliases) get reloaded.


### Examples
You can find the config file under `plugins/aliasr/config.toml`.

```toml
aliases = [
    { name = "switchserver", args = "(\\S*)", command = "server", commandArgs = "$1" } 
]
```
This will create a command `/switchserver`.
The command `/server` is gonna be an alias with the arguments of the first catch (which means the first argument, the server name) of RegEx.
