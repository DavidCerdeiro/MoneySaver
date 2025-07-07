// components/MobileNav.tsx
"use client"

import { Sheet, SheetTrigger, SheetContent } from "@/app/domains/shared/components/sheet.js"
import { Button } from "@/app/domains/shared/components/button.js"
import { Menu } from "lucide-react"
import { Link, useNavigate } from "react-router-dom"
import { useTranslation } from "react-i18next"

export function MobileNav() {
  const { t } = useTranslation()
  const navigate = useNavigate()

  const handleLogout = () => {
    localStorage.removeItem("authToken")
    navigate("/login")
  }

  return (
    <Sheet>
      <SheetTrigger asChild>
        <Button variant="ghost" size="icon">
          <Menu className="h-6 w-6" />
        </Button>
      </SheetTrigger>
      <SheetContent side="left">
        <nav className="flex flex-col space-y-4 mt-8">
          <Link to="/home" className="nav-link">Inicio</Link>

          <details className="group">
            <summary className="nav-link cursor-pointer">{t("header.sections.profile.title")}</summary>
            <div className="ml-4 mt-1 flex flex-col space-y-1">
              <Link to="/user/modifyProfile" className="nav-link">{t("header.sections.profile.editProfile.title")}</Link>
              <Link to="/user/linkBankAccount" className="nav-link">{t("header.sections.profile.linkBankAccount.title")}</Link>
              <Link to="/user/settings" className="nav-link">{t("header.sections.profile.settings.title")}</Link>
            </div>
          </details>

          <details className="group">
            <summary className="nav-link cursor-pointer">{t("header.sections.spendings.title")}</summary>
            <div className="ml-4 mt-1 flex flex-col space-y-1">
              <Link to="/spendings/view" className="nav-link">{t("header.sections.spendings.viewSpendings.title")}</Link>
              <Link to="/spendings/add" className="nav-link">{t("header.sections.spendings.addSpending.title")}</Link>
              <Link to="/categories/modify" className="nav-link">{t("header.sections.spendings.editCategory.title")}</Link>
              <Link to="/categories/add" className="nav-link">{t("header.sections.spendings.addCategory.title")}</Link>
            </div>
          </details>

          <details className="group">
            <summary className="nav-link cursor-pointer">{t("header.sections.savingGoals.title")}</summary>
            <div className="ml-4 mt-1 flex flex-col space-y-1">
              <Link to="/savingGoals/create" className="nav-link">{t("header.sections.savingGoals.createGoal.title")}</Link>
              <Link to="/savingGoals/view" className="nav-link">{t("header.sections.savingGoals.viewGoals.title")}</Link>
              <Link to="/savingGoals/edit" className="nav-link">{t("header.sections.savingGoals.editGoals.title")}</Link>
            </div>
          </details>

          <details className="group">
            <summary className="nav-link cursor-pointer">{t("header.sections.charts.title")}</summary>
            <div className="ml-4 mt-1 flex flex-col space-y-1">
              <Link to="/charts/view" className="nav-link">{t("header.sections.charts.viewCharts.title")}</Link>
              <Link to="/charts/compare" className="nav-link">{t("header.sections.charts.compareCharts.title")}</Link>
            </div>
          </details>

          <button onClick={handleLogout} className="logout mt-4">
            {t("header.logout")}
          </button>
        </nav>
      </SheetContent>
    </Sheet>
  )
}
