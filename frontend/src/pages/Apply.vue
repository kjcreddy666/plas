<template>
  <div class="container py-5" style="min-height: 85vh;">
    <h2 class="text-center mb-3">Loan Application</h2>
    <p class="text-center mt-4 mb-5 fs-5" v-if="!submitted">
      Complete your loan application in a few simple steps
    </p>

    <!-- Show success screen after submission -->
    <ApplicationSubmitted
      v-if="submitted"
      :name="name"
      @home="goHome"
      @dashboard="goDashboard"
    />

    <!-- Steps form -->
    <div v-if="!submitted">
      <div class="mb-5 mt-4">
        <StepIndicator :steps="steps" :currentStep="currentStep" />
      </div>

      <div class="mb-5" v-if="currentStep === 0">
        <Step2LoanDetails v-model:form="form" @next="nextStep" />
      </div>

      <div class="mb-5" v-if="currentStep === 1">
        <Step3FinancialInfo v-model:form="form" @next="nextStep" @prev="prevStep" />
      </div>

      <div class="mb-5" v-if="currentStep === 2">
        <Step4Review :form="form" @submit="submitForm" @prev="prevStep" />
        <div v-if="loanError" class="alert alert-danger mt-3 text-center">
          {{ loanError }}
        </div>
        <div v-if="loanLoading" class="text-center mt-3">
          <div class="spinner-border text-primary" role="status">
            <span class="visually-hidden">Loading...</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import StepIndicator from '../components/apply/LoanStepIndicator.vue'
import Step2LoanDetails from '../components/apply/LoanDetails.vue'
import Step3FinancialInfo from '../components/apply/FinancialInfo.vue'
import Step4Review from '../components/apply/Review.vue'
import ApplicationSubmitted from '../components/apply/ApplicationSubmitted.vue'

import { useLoan } from '@/composables/useLoan'

export default {
  components: {
    StepIndicator,
    Step2LoanDetails,
    Step3FinancialInfo,
    Step4Review,
    ApplicationSubmitted
  },
  data() {
    return {
      currentStep: 0,
      name: localStorage.getItem('userName') || '',
      submitted: false,
      steps: ["Loan Details", "Financial Info", "Review"],
      form: {
        userId: localStorage.getItem('userId') || '',
        amount: null,
        tenureMonths: null,
        purpose: '',
        income: null,
        creditScore: null
      },
      loanError: '',
      loanLoading: false
    }
  },
  methods: {
    nextStep() {
      if (this.currentStep < 2) this.currentStep++
    },
    prevStep() {
      if (this.currentStep > 0) this.currentStep--
    },
    async submitForm() {
      const { apply } = useLoan()
      this.loanLoading = true
      this.loanError = ''
      console.log(this)
      const response = await apply(this.form)
      this.loanLoading = false

      if (response?.isSuccess) {
        this.submitted = true
      } else {
        this.loanError = response.message || 'Submission failed.'
      }
    },
    goHome() {
      this.$router.push('/')
    },
    goDashboard() {
      this.$router.push('/dashboard')
    }
  }
}
</script>
