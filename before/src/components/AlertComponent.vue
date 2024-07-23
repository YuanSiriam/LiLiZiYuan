<script setup>
import {ref, defineExpose} from "vue";

const props = defineProps({
  message: {
    type: String,
    required: true,
  },
  duration: {
    type: Number,
    default: 3000,
  }
})

const isVisible = ref(false)

const showMessage = () => {
  isVisible.value = true
  setTimeout(() => {
    isVisible.value = false
  }, props.duration)
}

defineExpose({ showMessage })

</script>

<template>
  <transition name="fade">
    <div v-if="isVisible" role="alert" class="alert alert-success">
      <svg xmlns="http://www.w3.org/2000/svg"
           class="h-6 w-6 shrink-0 stroke-current"
           fill="none" viewBox="0 0 24 24">
        <path stroke-linecap="round" stroke-linejoin="round"
              stroke-width="2"
              d="M9 12l2 2 4-4m6 2a9 9 0
              11-18 0 9 9 0 0118 0z" />
      </svg>
      <span>{{ message }}</span>
    </div>
  </transition>
</template>

<style scoped lang="scss">

</style>