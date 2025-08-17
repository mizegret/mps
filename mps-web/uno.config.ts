import { defineConfig, presetIcons, presetWind3, presetAttributify, transformerVariantGroup } from 'unocss'

export default defineConfig({
  presets: [
    presetWind3(),
    presetIcons({
      autoInstall: true,
      mode: 'mask',
      extraProperties: {
        display: 'inline-block',
      },
    }),
    presetAttributify({
      prefix: 'uno-',
      prefixedOnly: true,
    }),
  ],
  transformers: [transformerVariantGroup()],
  shortcuts: [
    { 'flex-between': 'flex items-center justify-between' },
    { 'app-pad': 'p-2 sm:p-4' },
    {
      'input-text-base':
        'rounded border border-gray-300 px-3 py-2 outline-none ' +
        'placeholder:text-gray-400 focus:(ring-2 ring-gray-400) ' +
        'disabled:(opacity-60 cursor-not-allowed)',
    },
    {
      'button-base':
        'px-4 py-2 rounded-md bg-inherit hover:bg-gray-100 border-(2 gray-600) active:(ring-2 ring-gray-400) focus-visible:(outline outline-2 outline-offset-2)',
    },
    [/^flex-center-(\d+)$/, ([, n]) => `flex items-center gap-${n}`],
    [/^iflex-(\d+)$/, ([, n]) => `inline-flex items-center gap-${n}`],
    [
      /^trn-(transform|opacity)-(enter|leave)-(\d+)$/,
      ([, type, phase, ms]) => {
        const ease = phase === 'enter' ? 'ease-out' : 'ease-in'
        return `transition-${type} duration-${ms} ${ease} motion-reduce:transition-none`
      },
    ],
  ],
})
