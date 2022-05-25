[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=navikt_fpsoknad-felles&metric=alert_status)](https://sonarcloud.io/dashboard?id=navikt_fpsoknad-felles)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=navikt_fpsoknad-felles&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=navikt_fpsoknad-felles)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=navikt_fpsoknad-felles&metric=code_smells)](https://sonarcloud.io/dashboard?id=navikt_fpsoknad-felles)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=navikt_fpsoknad-felles&metric=security_rating)](https://sonarcloud.io/dashboard?id=navikt_fpsoknad-felles)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=navikt_fpsoknad-felles&metric=coverage)](https://sonarcloud.io/dashboard?id=navikt_fpsoknad-felles)
# fpsoknad-felles

Dette er et felles bibliotek som brukes av blandt annet
https://github.com/navikt/foreldrepengesoknad-api
https://github.com/navikt/fpsoknad-mottak
https://github.com/navikt/fpinfo-historikk
https://github.com/navikt/fpinfo
https://github.com/navikt/fpsak-autotest.


Biblioteket inneholder:
* Kontrakt mellom foreldrepengesoknad-api og fpsoknad-mottak
* Kontrakt mellom fpsoknad-mottak og fpinfo-historikk,
* Kontrakt mellom fpsoknad-mottak og fpinfo,
* Mapper fra JSON til XML søknadsobjekt (som sendes videre til fpfordel for journalføring),
* Mappere fra XML til JSON søknadsobjekt for bruk i innsynsløsnignen,
* Utilities og søknadsbygger.
