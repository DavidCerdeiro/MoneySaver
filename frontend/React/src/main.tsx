import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { LoginForm } from './app/domains/auth/components/LoginForm.js'
import { SignUpForm } from './app/domains/auth/components/SignUpForm.js'
import { ForgotPasswordForm } from './app/domains/auth/components/ForgotPasswordForm.js'
import { VerificationCodeForm } from './app/domains/auth/components/VerificationCodeForm.js'
import { BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import './index.css'
import './i18n/index.ts'
import { ResetPasswordForm } from './app/domains/auth/components/ResetPasswordForm.tsx'
import { UserProvider } from './app/contexts/UserContext.tsx'
import { DashboardPage } from './app/domains/auth/pages/DashboardPage.tsx'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <UserProvider>
      <Router>
        <Routes>
          <Route path="/login" element={<LoginForm />} />
          <Route path="/login/authUser" element={<VerificationCodeForm source='login'/>} />

          <Route path="/" element={<SignUpForm />} />
          <Route path="/authUser" element={<VerificationCodeForm source="signup" />} />

          <Route path="/forgot-password" element={<ForgotPasswordForm/>} />
          <Route path="/forgot-password/authUser" element={<VerificationCodeForm source = "forgot"/>} />
          <Route path="/forgot-password/reset-password" element={<ResetPasswordForm />} />

          <Route path="/dashboard" element={<DashboardPage />} />
        </Routes>
      </Router>
    </UserProvider>
  </StrictMode>,
)