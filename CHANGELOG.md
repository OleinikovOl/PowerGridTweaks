# Changelog

## 0.1.1 - Minecraft 1.21.1

- Обновлён `shaft_generator`: добавлена новая Blockbench-модель, обновлены block/item models, blockstate и runtime-текстура.
- Вал генератора теперь отрисовывается через Create/Flywheel и вращается от скорости `ShaftGeneratorBlockEntity`; item model оставлен со статичным shaft для инвентаря.
- Обновлены pins/connectors генератора, voxel shape и рецепт крафта под новую модель.
- Обновлены и синхронизированы текстуры генератора, lithium/material items и PSD source assets.
- Добавлен и приведён в порядок art pipeline для текстур: исходники, синхронизация PNG и проверка `validateTextures`.
- Полностью удалён miner helmet вместе с FE-питанием, armor material, capabilities, data components, рецептом, моделями, текстурами и тестами.
- Регистрации блоков и предметов вынесены в доменные классы с сохранением совместимых фасадов `ModBlocks` и `ModItems`.
- Подчищены неиспользуемые методы, константы, импорты и конфигурация после удаления miner helmet.
- Добавлены тесты логики порогов relay.
