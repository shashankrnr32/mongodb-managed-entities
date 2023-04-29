# mongodb-managed-entities

[![Java CI with Maven](https://github.com/shashankrnr32/mongodb-managed-entities/actions/workflows/maven.yml/badge.svg)](https://github.com/shashankrnr32/mongodb-managed-entities/actions/workflows/maven.yml)
[![Maven Package](https://github.com/shashankrnr32/mongodb-managed-entities/actions/workflows/maven-publish.yml/badge.svg)](https://github.com/shashankrnr32/mongodb-managed-entities/actions/workflows/maven-publish.yml)
[![codecov](https://codecov.io/gh/shashankrnr32/mongodb-managed-entities/branch/main/graph/badge.svg?token=U51FX5G10S)](https://codecov.io/gh/shashankrnr32/mongodb-managed-entities)

[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-black.svg)](https://sonarcloud.io/summary/new_code?id=shashankrnr32_mongodb-managed-entities)

MongoDB managed entities allows the user to add proxied implementations for entities without having to actually
implement them. The library provides multiple interfaces given below which are implemented by proxy advices within the
library.

## Get Started

To get started, add this library as a dependency in your project.

```xml
<!-- managed-entities -->
<dependency>
    <groupId>com.alpha.mongodb</groupId>
    <artifactId>managed-entities</artifactId>
    <version>${managed.entities.version}</version>
</dependency>
```

## Usage

To know the usage of the library, refer to the example given in the example application [here](managed-entities-example)

## Features

1. Manageable Entities with proxied implementations
    1. Cloneable
    2. Deletable
    3. Manageable
    4. Refreshable
    5. Updatable

## Roadmap

1. New proxied entities
    1. Persistable
    2. Constructable
    3. and many more...!
2. Manageable Entity List
3. Get "relational" entities from a managed entity

## Code Analysis

[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=shashankrnr32_mongodb-managed-entities&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=shashankrnr32_mongodb-managed-entities)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=shashankrnr32_mongodb-managed-entities&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=shashankrnr32_mongodb-managed-entities)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=shashankrnr32_mongodb-managed-entities&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=shashankrnr32_mongodb-managed-entities)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=shashankrnr32_mongodb-managed-entities&metric=bugs)](https://sonarcloud.io/summary/new_code?id=shashankrnr32_mongodb-managed-entities)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=shashankrnr32_mongodb-managed-entities&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=shashankrnr32_mongodb-managed-entities)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=shashankrnr32_mongodb-managed-entities&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=shashankrnr32_mongodb-managed-entities)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=shashankrnr32_mongodb-managed-entities&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=shashankrnr32_mongodb-managed-entities)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=shashankrnr32_mongodb-managed-entities&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=shashankrnr32_mongodb-managed-entities)

## Author

[Shashank Sharma](https://github.com/shashankrnr32)

