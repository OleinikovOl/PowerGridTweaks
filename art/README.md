# Арт-пайплайн PowerGridTweaks

## Рабочий процесс

Дизайнеру больше не нужно создавать отдельный PSD-проект под каждый ассет. Вместо этого используем мастер-файлы по категориям:

- `art/sources/psd/items.psd`
- `art/sources/psd/blocks.psd`
- `art/sources/psd/gui.psd`
- `art/sources/psd/machines.psd`

Референсы хранятся в `art/sources/references/`. Исходники Aseprite можно хранить в `art/sources/aseprite/`.

Готовые PNG нужно экспортировать в:

```text
art/generated/textures/<category>/<texture_name>.png
```

Gradle копирует экспортированные PNG в Minecraft resources:

```text
src/main/resources/assets/powergridtweaks/textures/
```

## Примеры

```text
art/generated/textures/item/lithium_cell.png
art/generated/textures/item/incomplete_lithium_cell.png
art/generated/textures/block/battery_controller_front.png
art/generated/textures/block/battery_controller_side.png
art/generated/textures/gui/battery_controller.png
```

## Правила именования

- Только lowercase.
- Использовать `snake_case`.
- Без пробелов.
- Без кириллицы.
- Без заглавных букв.
- Для экспортированных текстур использовать только расширение `.png`.
- Допустимые символы в пути: `a-z`, `0-9`, `_`, `-`, `/` и `.`.

Обычные Minecraft-текстуры предметов и блоков обычно должны быть `16x16`, если проект намеренно не использует другой масштаб. Валидатор также принимает `32x32`, `64x64`, `128x128` и `256x256`. GUI-текстуры могут иметь нестандартный размер; для них валидатор выводит предупреждение, но не ошибку.

## Photoshop Generator

Photoshop Generator может экспортировать PNG из групп или слоев, если назвать группу или слой как путь к PNG, например:

```text
item/lithium_cell.png
block/battery_controller_front.png
```

Экспортируйте эти файлы внутрь `art/generated/textures/`.

## Команды

Для разработчика:

```bash
./gradlew syncTextures
./gradlew validateTextures
./gradlew processResources
```

На Windows:

```bat
gradlew.bat syncTextures
gradlew.bat validateTextures
gradlew.bat processResources
```

Для дизайнера:

1. Открыть мастер-файл PSD из `art/sources/psd/`.
2. Обновить нужную группу или слой.
3. Экспортировать PNG в `art/generated/textures/`.
4. Запустить `./gradlew syncTextures` или попросить разработчика запустить сборку.

`art/generated/textures/**/*.png` — это staging-зона, она игнорируется git. Коммитить нужно синхронизированные PNG из `src/main/resources/assets/powergridtweaks/textures/`.
