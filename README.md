# README.md

## Project design

```txt
docker
  (debugging) -> maven
                -> spring-boot-devtools
  (build)     -> maven
                -> package
  (run app)   -> java -jar
                -> spring-boot
```

## Develop

### Recommended environment

> You can install these by [SDKMAN!](https://sdkman.io)
> ```
> curl -s "https://get.sdkman.io" | bash
> sdk i java 11.0.10.hs-adpt
> sdk i maven 3.6.3
> ```

- JDK 11.0.10 hotspot
  - use AdoptOpenJDK
- Apache Maven 3.6.3

## Troubleshoot memo

### MSYS2/Git Bash(Git for Windows)

- Do not use `MSYS2_ARG_CONV_EXCL` or disable that value on top of script like `mvn`/`mvnw`
  - Simple solution: remove environment variable `MSYS2_ARG_CONV_EXCL` from system, `.bashrc`, `.profile`, and so on.
  - If you want to use `MSYS2_ARG_CONV_EXCL='*'` or like it for some other reason, you can disable the variable on top of script. Add `export` command just after shebang like:

    ```sh
    #!/bin/sh
    export MSYS2_ARG_CONV_EXCL=''

    # ...
    ```

- Character encoding error(文字化け)
  - Many java tools output the error message to STDERR not STDOUT. If you use `iconv` command, may need redirect not only STDOUT but also STDERR to `iconv` like:

    ```sh
    mvn install 2>&1 | iconv -f sjis -t utf-8
    ```
