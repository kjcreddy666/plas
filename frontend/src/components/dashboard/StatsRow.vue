<template>
    <div class="row g-3 mb-4">
      <StatCard
        v-for="stat in stats"
        :key="stat.label"
        :label="stat.label"
        :value="stat.value"
        :cssClass="stat.class"
      />
    </div>
</template>
  
<script setup>
  import StatCard from './StatCard.vue';
  defineProps({ stats: Array });
</script>