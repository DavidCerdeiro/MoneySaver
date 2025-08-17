import './index.css'
import './i18n/index.ts'

import { LoginPage } from './app/domains/auth/pages/LoginPage.tsx'
import { ForgotPasswordPage } from './app/domains/auth/pages/ForgotPasswordPage.tsx'
import { ResetPasswordPage } from './app/domains/auth/pages/ResetPasswordPage.tsx';
import { VerificationEmailPage } from './app/domains/auth/pages/VerificationEmailPage.tsx'
import { SignUpPage } from './app/domains/auth/pages/SignUpPage.tsx';
import { MainPage } from './app/domains/shared/pages/MainPage.tsx'


import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import { UserProvider } from './app/contexts/UserContext.tsx'
import { Toaster } from './app/domains/shared/components/Toaster.tsx'
import { AddCategoryPage } from './app/domains/category/pages/AddCategoryPage.tsx';
import { ModifyCategoryPage } from './app/domains/category/pages/ModifyCategoryPage.tsx';
import { AddSpendingPage } from './app/domains/spending/pages/AddSpendingPage.tsx';
import { ViewSpendingsPage } from './app/domains/spending/pages/ViewSpendingsPage.tsx';
import { ModifyProfilePage } from './app/domains/user/pages/ModifyProfilePage.tsx';
import { DeleteProfilePage } from './app/domains/user/pages/DeleteProfilePage.tsx';
import { ViewChartsPage } from './app/domains/charts/pages/ViewChartsPage.tsx';
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
          <Route path="/categories/modify" element={<ModifyCategoryPage />} />

          <Route path="/spendings/add" element={<AddSpendingPage />} />
          <Route path="/spendings/view" element={<ViewSpendingsPage />} />

          <Route path="/user/modifyProfile" element={<ModifyProfilePage />} />
          <Route path="/user/deleteProfile" element={<DeleteProfilePage />} />

          <Route path="/charts/view" element={<ViewChartsPage />} />
        </Routes>
      </Router>
      <Toaster />
    </UserProvider>
  </StrictMode>,
)