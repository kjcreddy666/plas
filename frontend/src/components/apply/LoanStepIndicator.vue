<template>
    <div class="d-flex justify-content-center mb-4">
      <template v-for="(stepLabel, index) in steps" :key="index">
        <div class="text-center mx-2">
          <div
            :class="[
              'rounded-circle p-2',
              currentStep > index
                ? 'bg-success text-white'
                : currentStep === index
                ? 'bg-primary text-white'
                : 'bg-light',
            ]"
            style="width: 40px; height: 40px"
          >
            {{ index + 1 }}
          </div>
          <small>{{ stepLabel }}</small>
        </div>
        <div
          v-if="index < steps.length - 1"
          class="align-self-center mx-2 border-top border-2"
          style="width: 40px"
        ></div>
      </template>
    </div>
  </template>
  
  <script>
  export default {
    props: ['steps', 'currentStep']
  }
  </script>
  