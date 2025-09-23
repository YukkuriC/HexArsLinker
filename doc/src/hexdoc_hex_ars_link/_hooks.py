# type: ignore
from importlib.resources import Package
from typing_extensions import override

from hexdoc.plugin import (
    HookReturn,
    ModPlugin,
    ModPluginImpl,
    ModPluginWithBook,
    hookimpl,
)

import hexdoc_hex_ars_link

from .__gradle_version__ import FULL_VERSION, GRADLE_VERSION
from .__version__ import PY_VERSION


class HexArsLinkerPlugin(ModPluginImpl):
    @staticmethod
    @hookimpl
    def hexdoc_mod_plugin(branch: str) -> ModPlugin:
        HexArsLinkerPlugin.disable_custom_pages()
        return HexArsLinkerModPlugin(branch=branch)

    @staticmethod
    def disable_custom_pages():
        from hexdoc.patchouli import Entry

        @classmethod
        def wrap_load(cls, resource_dir, id, data, context):
            data['pages'] = list(
                filter(
                    (
                        lambda p: isinstance(p, str)
                        or not p['type'].startswith('hex_ars_link:')
                    ),
                    data['pages'],
                )
            )
            return Entry._load_original(resource_dir, id, data, context)

        Entry._load_original = Entry.load
        Entry.load = wrap_load


class HexArsLinkerModPlugin(ModPluginWithBook):
    @property
    @override
    def modid(self) -> str:
        return "hex_ars_link"

    @property
    @override
    def full_version(self) -> str:
        return FULL_VERSION

    @property
    @override
    def mod_version(self) -> str:
        return GRADLE_VERSION

    @property
    @override
    def plugin_version(self) -> str:
        return PY_VERSION

    @override
    def resource_dirs(self) -> HookReturn[Package]:
        # lazy import because generated may not exist when this file is loaded
        # eg. when generating the contents of generated
        # so we only want to import it if we actually need it
        from ._export import generated

        return generated

    @override
    def jinja_template_root(self) -> tuple[Package, str]:
        return hexdoc_hex_ars_link, "_templates"
