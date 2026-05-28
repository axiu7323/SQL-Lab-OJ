<template>
  <div ref="editorRef" class="sql-editor" :style="{ height }" />
</template>

<script setup lang="ts">
import * as monaco from 'monaco-editor'
import { onBeforeUnmount, onMounted, ref, watch } from 'vue'

const props = withDefaults(
  defineProps<{
    modelValue: string
    readonly?: boolean
    height?: string
  }>(),
  {
    readonly: false,
    height: '360px'
  }
)

const emit = defineEmits<{
  'update:modelValue': [value: string]
}>()

const editorRef = ref<HTMLElement>()
let editor: monaco.editor.IStandaloneCodeEditor | undefined

onMounted(() => {
  if (!editorRef.value) {
    return
  }
  editor = monaco.editor.create(editorRef.value, {
    value: props.modelValue,
    language: 'sql',
    theme: 'vs',
    readOnly: props.readonly,
    minimap: { enabled: false },
    fontSize: 14,
    lineNumbersMinChars: 3,
    automaticLayout: true,
    scrollBeyondLastLine: false
  })
  editor.onDidChangeModelContent(() => {
    emit('update:modelValue', editor?.getValue() || '')
  })
})

watch(
  () => props.modelValue,
  (value) => {
    if (editor && value !== editor.getValue()) {
      editor.setValue(value)
    }
  }
)

watch(
  () => props.readonly,
  (readonly) => {
    editor?.updateOptions({ readOnly: readonly })
  }
)

onBeforeUnmount(() => {
  editor?.dispose()
})
</script>

<style scoped>
.sql-editor {
  width: 100%;
  overflow: hidden;
  border: 1px solid #d6dbe6;
  border-radius: 8px;
}
</style>
