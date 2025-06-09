import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { LoginForm } from './app/domains/auth/components/LoginForm.js'
import { SignUpForm } from './app/domains/auth/components/SignUpForm.js'
import { ForgotPasswordForm } from './app/domains/auth/components/ForgotPasswordForm.js'
import { BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import './index.css'
import './i18n/index.ts'


createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <Router>
      <Routes>
        <Route path="/login" element={<LoginForm />} />
        <Route path="/" element={<SignUpForm />} />
        <Route path="/forgot-password" element={<ForgotPasswordForm />} />
      </Routes>
    </Router>
  </StrictMode>,
)