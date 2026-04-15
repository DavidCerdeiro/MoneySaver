import { Sheet, SheetTrigger, SheetContent } from "@/app/domains/shared/components/sheet.js"
import { Button } from "@/app/domains/shared/components/button.js"
import { Menu } from "lucide-react"
import { Link, useNavigate } from "react-router-dom"
import { useTranslation } from "react-i18next"
import { logout } from "../../user/application/UserService"

export function MobileNav() {
  const { t, i18n} = useTranslation()
  const navigate = useNavigate()
  const handleLogout = () => {
      logout(); 
      navigate('/')
  }
  
  const handleChangeLang = (lng: string) => {
    i18n.changeLanguage(lng);
  }
  return (
    <Sheet>
      <SheetTrigger asChild>
        <Button variant="ghost" size="icon">
          <Menu className="h-6 w-6" />
        </Button>
      </SheetTrigger>
      <SheetContent side="left" className="bg-black text-gray-200 border-none p-4">
        <nav className="flex flex-col space-y-2 mt-8">
          <Link to="/home" className="mb-4">
            <h1 className="text-xl font-bold">{t("app.title")}</h1>
          </Link>
          <div className="flex items-center gap-4">
            <div className="flex items-center gap-2">
              <button 
                onClick={() => handleChangeLang("es")} 
                className={`font-semibold text-sm transition-colors hover:text-white ${
                  i18n.language === 'es' ? 'text-white' : 'text-zinc-400'
                }`}
              >
                ES
              </button>
              <div className="h-4 w-px bg-zinc-600"></div>
              <button 
                onClick={() => handleChangeLang("en")} 
                className={`font-semibold text-sm transition-colors hover:text-white ${
                  i18n.language === 'en' ? 'text-white' : 'text-zinc-400'
                }`}
              >
                EN
              </button>
            </div>
          </div>
          <details className="group">
            <summary className="nav-link cursor-pointer list-none flex items-center justify-between p-2 rounded-md hover:bg-gray-800">
              {t("header.sections.profile.title")}
              <span className="transition-transform duration-200 group-open:rotate-90">&#9656;</span>
            </summary>
            <div className="ml-4 mt-1 flex flex-col space-y-1 border-l border-gray-700 pl-4">
              <Link to="/user/profile/edit" className="nav-link-mobile">{t("header.sections.profile.editProfile.title")}</Link>
              <Link to="/user/accounts" className="nav-link-mobile">{t("header.sections.profile.manageAccounts.title")}</Link>
            </div>
          </details>

          <details className="group">
            <summary className="nav-link cursor-pointer list-none flex items-center justify-between p-2 rounded-md hover:bg-gray-800">
              {t("header.sections.spendings.title")}
              <span className="transition-transform duration-200 group-open:rotate-90">&#9656;</span>
            </summary>
            <div className="ml-4 mt-1 flex flex-col space-y-1 border-l border-gray-700 pl-4">
              <Link to="/spendings/view" className="nav-link-mobile">{t("header.sections.spendings.viewSpendings.title")}</Link>
              <Link to="/spendings/add" className="nav-link-mobile">{t("header.sections.spendings.addSpending.title")}</Link>
              <Link to="/categories/edit" className="nav-link-mobile">{t("header.sections.spendings.editCategory.title")}</Link>
              <Link to="/categories/add" className="nav-link-mobile">{t("header.sections.spendings.addCategory.title")}</Link>
              <Link to="/spendings/transactions" className="nav-link-mobile">{t("header.sections.spendings.transactions.title")}</Link>
            </div>
          </details>

          <details className="group">
            <summary className="nav-link cursor-pointer list-none flex items-center justify-between p-2 rounded-md hover:bg-gray-800">
              {t("header.sections.savingGoals.title")}
              <span className="transition-transform duration-200 group-open:rotate-90">&#9656;</span>
            </summary>
            <div className="ml-4 mt-1 flex flex-col space-y-1 border-l border-gray-700 pl-4">
              <Link to="/savingGoals/add" className="nav-link-mobile">{t("header.sections.savingGoals.createGoal.title")}</Link>
              <Link to="/savingGoals/view" className="nav-link-mobile">{t("header.sections.savingGoals.viewGoals.title")}</Link>
              <Link to="/savingGoals/edit" className="nav-link-mobile">{t("header.sections.savingGoals.editGoals.title")}</Link>
            </div>
          </details>

          <details className="group">
            <summary className="nav-link cursor-pointer list-none flex items-center justify-between p-2 rounded-md hover:bg-gray-800">
              {t("header.sections.charts.title")}
              <span className="transition-transform duration-200 group-open:rotate-90">&#9656;</span>
            </summary>
            <div className="ml-4 mt-1 flex flex-col space-y-1 border-l border-gray-700 pl-4">
              <Link to="/charts/view" className="nav-link-mobile">{t("header.sections.charts.viewCharts.title")}</Link>
              <Link to="/charts/compare" className="nav-link-mobile">{t("header.sections.charts.compareCharts.title")}</Link>
            </div>
          </details>

          <button onClick={handleLogout} className="mt-6 w-full text-left p-2 rounded-md text-red-500 hover:bg-gray-800 font-bold">
            {t("header.logout")}
          </button>
        </nav>
      </SheetContent>
    </Sheet>
  )
}