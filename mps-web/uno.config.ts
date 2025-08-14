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
    [/^iflex-(\d+)$/, ([, n]) => `inline-flex items-center gap-${n}`],
  ],
})
