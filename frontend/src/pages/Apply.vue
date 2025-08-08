<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import StepIndicator from '../components/apply/LoanStepIndicator.vue'
import Step2LoanDetails from '../components/apply/LoanDetails.vue'
import Step3FinancialInfo from '../components/apply/FinancialInfo.vue'
import Step4Review from '../components/apply/Review.vue'
import ApplicationSubmitted from '../components/apply/ApplicationSubmitted.vue'
import { useLoan } from '@/composables/useLoan'

const router = useRouter()

const userId = ref(localStorage.getItem('userId'))
const token = ref(localStorage.getItem('userToken'))
const role = ref(localStorage.getItem('userRole'))

onMounted(() => {
  if (!token.value || !userId.value || !role.value) {
    router.push('/auth')
  } else if (role.value !== 'CUSTOMER') {
    router.push('/unauthorized')
  }
})

const currentStep = ref(0)
const submitted = ref(false)
const loanError = ref('')
const loanLoading = ref(false)

const name = ref(localStorage.getItem('userName') || '')

const steps = ["Loan Details", "Financial Info", "Review"]

const form = ref({
  userId: localStorage.getItem('userId') || '',
  name: localStorage.getItem('userName') || '',
  email: localStorage.getItem('userEmail') || '',
  mobile: localStorage.getItem('userMobile') || '',
  amount: null,
  tenureMonths: null,
  purpose: '',
  income: null,
  creditScore: null
})

const nextStep = () => {
  if (currentStep.value < 2) currentStep.value++
}
const prevStep = () => {
  if (currentStep.value > 0) currentStep.value--
}

const goHome = () => {
  router.push('/')
}
const goDashboard = () => {
  router.push('/dashboard')
}

const submitForm = async () => {
  const { apply } = useLoan()
  loanError.value = ''
  loanLoading.value = true

  const loanApplicationData = {
    userId: form.value.userId,
    amount: form.value.amount,
    tenureMonths: form.value.tenureMonths,
    purpose: form.value.purpose,
    income: form.value.income,
    creditScore: form.value.creditScore
  }

  console.log('Submitting loan application:', loanApplicationData)
  const response = await apply(loanApplicationData)
  loanLoading.value = false

  if (response?.isSuccess) {
    submitted.value = true
  } else {
    loanError.value = response.message || 'Submission failed.'
  }
}
</script>

<template>
  <div class="container-fluid py-5 min-vh-100">
    <h2 class="text-center mb-3">Loan Application</h2>
    <p class="text-center mt-4 mb-5 fs-5" v-if="!submitted">
      Complete your loan application in a few simple steps
    </p>

    <ApplicationSubmitted
      v-if="submitted"
      :name="name"
      @home="goHome"
      @dashboard="goDashboard"
    />

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
