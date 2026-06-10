# Заметки по миграции текстур

Существующие текстуры оставлены в `src/main/resources/assets/powergridtweaks/textures/`. Их не нужно перемещать автоматически. Когда появится время, стоит воссоздать или разложить их исходные слои в новых мастер-файлах PSD/Aseprite, а затем экспортировать замены через `art/generated/textures/`.

## Найденные текстуры

### Блоки

- `block/lithium_battery_side_connected.png`
- `block/lithium_battery_side.png`
- `block/lithium_battery_top_connected.png`
- `block/lithium_battery_top.png`
- `block/nether_lithium_ore.png`
- `block/shaft_generator.png`

### Предметы

- `item/crushed_lithium.png`
- `item/graphite_sheet.png`
- `item/incomplete_graphite.png`
- `item/incomplete_lithium_cell.png`
- `item/lithium_cell.png`
- `item/lithium_ingot.png`
- `item/lithium_nugget.png`
- `item/lithium_sheet.png`
- `item/miner_helmet.png`
- `item/raw_lithium.png`

### Слой брони

- `models/armor/miner_helmet_layer_1.png`

## Рекомендации по ручной миграции

- Добавить текстуры предметов в `art/sources/psd/items.psd`.
- Добавить текстуры блоков в `art/sources/psd/blocks.psd`.
- Добавить текстуры брони и машин в `art/sources/psd/machines.psd`, если позже не появится отдельный мастер-файл для брони.
- Экспортировать обновленные PNG в `art/generated/textures/` и запустить `./gradlew syncTextures`.
