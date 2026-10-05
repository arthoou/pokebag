# Pokebag

Mod Forge 1.12.2 standalone feito para interagir com Pixelmon.

Autor: arthou

## Itens

- Pokebag Normal: 18 slots / 2 linhas, equivalente a mochila basica.
- Great Pokebag: 27 slots / 3 linhas, equivalente a mochila de ferro.
- Ultra Pokebag: 36 slots / 4 linhas, equivalente a mochila de ouro.
- Master Pokebag: 54 slots / 6 linhas, equivalente a mochila de diamante.

As Pokebags sao itens, nao blocos, nao sao colocaveis no chao e nao usam slot de armadura/costas.

## Filtro

A Pokebag aceita apenas:

- berries do Pixelmon;
- pocoes do Minecraft/Pixelmon;
- Poke Balls do Pixelmon.

O filtro usa IDs, Ore Dictionary e nomes de classes do Pixelmon para continuar standalone, sem depender do Pixelmon em tempo de compilacao.

## Pixelmon

O mod ja expoe o conteudo da Pokebag por capability `IItemHandler`, alem da classe `PixelmonBattleBridge` para a integracao direta com a tela de batalha. Para conectar no ponto exato da batalha sem chute, envie o fonte/JAR do Pixelmon 1.12.2 usado no modpack.

## Build

Forge 1.12.2 usa ForgeGradle 2.3 e Gradle 4.9. Compile com Java 8:

```powershell
.\gradlew.bat build
```
