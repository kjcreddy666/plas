<template>
    <section id="feature-section" class="container-fluid d-flex flex-column justify-content-center align-items-center text-center px-3 py-5">
      <h1>Why Choose LoanFlow?</h1>
      <p style="max-width: 600px;">
        Experience a streamlined loan application process designed with your convenience in mind.
      </p>

      <div class="d-flex flex-wrap justify-content-center gap-3 mt-3">
        <FeatureCard
          iconClass="bi bi-lightning-fill"
          title="Fast Processing"
          description="Get your loan processed faster with our smart automation."
        />
        <FeatureCard
          iconClass="bi bi-shield-lock"
          title="Secure Application"
          description="Your information is protected with top-tier encryption."
        />
        <FeatureCard
          iconClass="bi bi-clock"
          title="24/7 Service"
          description="Apply anytime, anywhere, with our always-on platform."
        />
        <FeatureCard
          iconClass="bi bi-cash-stack"
          title="Affordable Rates"
          description="Benefit from competitive loan interest rates."
        />
        <FeatureCard
          iconClass="bi bi-phone"
          title="Mobile Friendly"
          description="Use our responsive interface across all devices."
        />
        <FeatureCard
          iconClass="bi bi-people"
          title="Expert Support"
          description="Our team is here to guide you through the process."
        />
      </div>
    </section>
  </template>
  
  <script setup>
  import FeatureCard from './FeatureCard.vue'
  </script>
  