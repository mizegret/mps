import { defineConfig, presetIcons, presetWind3, presetAttributify } from 'unocss'

export default defineConfig({
  presets: [
    presetWind3(),
    presetIcons({
      autoInstall: true,
    }),
    presetAttributify({
      prefix: 'uno-',
      prefixedOnly: true,
    }),
  ],
  shortcuts: [
    { 'flex-between': 'flex items-center justify-between' },
    { 'app-pad': 'p-4 sm:p-6' },
    {
      'input-text-base':
        'rounded border border-gray-300 px-3 py-2 outline-none ' +
        'placeholder:text-gray-400 focus:(ring-2 ring-gray-400) ' +
        'disabled:(opacity-60 cursor-not-allowed)',
    },
    [/^iflex-(\d+)$/, ([, n]) => `inline-flex items-center gap-${n}`],
  ],
})
