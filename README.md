# 🧶 untangle

Find usage of classes, packages in legacy and messy projects.

So you've inherited a bunch of java libraries widely used across your organization.
Of course there is little to no documentation, you don't know how and where your libraries are used.
But you need to make modifications and refactors and you *know* that something will get broken.

With **untangle** you can inspect all the jars and classes to find the usage of your libraries to check what you can or can't change.

---

[![build](https://github.com/ilsasdo/untangle/actions/workflows/build.yml/badge.svg)](https://github.com/ilsasdo/untangle/actions/workflows/build.yml)

## Usage

```shell
java -jar untangle.jar [options] [jar files | class files | directories]
```

## Examples

Find usages of `my.company.api.Product` class in all jars in current directory:
```shell
java -jar untangle.jar --class my.company.api.Product *.jar
```

Find usages of all classess under `my.company.api` package in all jars in current directory:
```shell
java -jar untangle.jar --package my.company.api *.jar
```

Find usages of all classess under `my.company.api` package in all classes:
```shell
java -jar untangle.jar --package my.company.api *.class
```

Find usages of all classess under `my.company.api` package in clasess and jars in the directory:
```shell
java -jar untangle.jar --package my.company.api WEB-INF/classes
```


