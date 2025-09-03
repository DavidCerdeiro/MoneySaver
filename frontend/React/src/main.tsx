import './index.css'
import './i18n/index.ts'
import './styles/utilities.css'
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
import { EditCategoryPage } from './app/domains/category/pages/EditCategoryPage.tsx';
import { AddSpendingPage } from './app/domains/spending/pages/AddSpendingPage.tsx';
import { ViewSpendingsPage } from './app/domains/spending/pages/ViewSpendingsPage.tsx';
import { EditProfilePage } from './app/domains/user/pages/EditProfilePage.tsx';
import { DeleteProfilePage } from './app/domains/user/pages/DeleteProfilePage.tsx';
import { ViewChartsPage } from './app/domains/charts/pages/ViewChartsPage.tsx';
import { CompareChartsPage } from './app/domains/charts/pages/CompareChartsPage.tsx';
import { PrivateRoute } from './app/contexts/PrivateRoute.tsx';
import { AddGoalPage } from './app/domains/goals/pages/AddGoalPage.tsx';
import { EditGoalsPage } from './app/domains/goals/pages/EditGoalsPage.tsx';
import { ViewGoalsPage } from './app/domains/goals/pages/ViewGoalsPage.tsx';
import { CallbackPage } from './app/domains/shared/pages/CallbackPage.tsx';
import { AccountsPage } from './app/domains/account/pages/AccountsPage.tsx';
import { ExtractTransactionsPage } from './app/domains/transactions/pages/ExtractTransactionsPage.tsx';
import { AddTransactionsPage } from './app/domains/transactions/pages/AddTransactionsPage.tsx';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <UserProvider>
      <Router>
        <Routes>
          <Route path="/login" element={<LoginPage />} />
          <Route path="/login/authUser" element={<VerificationEmailPage />} />
          <Route path="/" element={<SignUpPage />} />
          <Route path="/forgot-password" element={<ForgotPasswordPage />} />
          <Route path="/authUser" element={<VerificationEmailPage />} />
          <Route path="/forgot-password/authUser" element={<VerificationEmailPage />} />
          <Route path="/forgot-password/reset-password" element={<ResetPasswordPage />} />

          <Route path="/callback" element={<CallbackPage />} />
          
          {/* Protected Routes */}
          <Route element={<PrivateRoute />}>

            <Route path="/home" element={<MainPage />} />

            <Route path="/categories/add" element={<AddCategoryPage />} />
            <Route path="/categories/edit" element={<EditCategoryPage />} />

            <Route path="/spendings/add" element={<AddSpendingPage />} />
            <Route path="/spendings/view" element={<ViewSpendingsPage />} />
            <Route path="/spendings/transactions" element={<ExtractTransactionsPage />} />
            <Route path="/transactions/add" element={<AddTransactionsPage />} />

            <Route path="/user/profile/edit" element={<EditProfilePage />} />
            <Route path="/user/profile/delete" element={<DeleteProfilePage />} />
            <Route path="/user/accounts" element={<AccountsPage />} />

            <Route path="/charts/view" element={<ViewChartsPage />} />
            <Route path="/charts/compare" element={<CompareChartsPage />} />

            <Route path="/savingGoals/add" element={<AddGoalPage />} />
            <Route path="/savingGoals/edit" element={<EditGoalsPage />} />
            <Route path="/savingGoals/view" element={<ViewGoalsPage />} />
          </Route>
        </Routes>
      </Router>
      <Toaster />
    </UserProvider>
  </StrictMode>,
)