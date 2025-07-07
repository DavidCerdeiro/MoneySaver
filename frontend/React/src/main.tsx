import './index.css'

import { LoginPage } from './app/domains/auth/pages/LoginPage.tsx'
import { ForgotPasswordPage } from './app/domains/auth/pages/ForgotPasswordPage.tsx'
import { ResetPasswordPage } from './app/domains/auth/pages/ResetPasswordPage.tsx';
import { VerificationEmailPage } from './app/domains/auth/pages/VerificationEmailPage.tsx'
import { SignUpPage } from './app/domains/auth/pages/SignUpPage.tsx';
import { MainPage } from './app/domains/shared/pages/MainPage.tsx'

import './i18n/index.ts'
import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import { UserProvider } from './app/contexts/UserContext.tsx'
import { Toaster } from './app/domains/shared/components/Toaster.tsx'
import { AddCategoryPage } from './app/domains/category/pages/AddCategoryPage.tsx';


createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <UserProvider>
      <Router>
        <Routes>
          <Route path="/login" element={<LoginPage />} />
          <Route path="/login/authUser" element={<VerificationEmailPage />} />
          <Route path="/" element={<SignUpPage />} />
          <Route path="/authUser" element={<VerificationEmailPage />} />

          <Route path="/forgot-password" element={<ForgotPasswordPage />} />
          <Route path="/forgot-password/authUser" element={<VerificationEmailPage />} />
          <Route path="/forgot-password/reset-password" element={<ResetPasswordPage />} />

          <Route path="/home" element={<MainPage />} />

          <Route path="/categories/add" element={<AddCategoryPage />} />
        </Routes>
      </Router>
      <Toaster />
    </UserProvider>
  </StrictMode>,
)