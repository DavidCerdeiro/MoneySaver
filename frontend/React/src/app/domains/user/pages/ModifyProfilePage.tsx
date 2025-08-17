import { useTranslation } from 'react-i18next';
import { DefaultPageLayout } from "../../shared/layouts/DefaultPageLayout";
import { ModifyProfileForm } from '../components/ModifyProfileForm';
import { useEffect, useState } from 'react';
import { obtainUserProfile } from '../application/UserService';
import type { UserData } from '../schemas/User';
import type { TypeChartData } from '../../charts/schemas/TypeChart';
import { getAllTypeCharts } from '../../charts/application/ChartService';
export function ModifyProfilePage() {
  const { t } = useTranslation();
  const [user, setUser] = useState<UserData | null>(null);
  const [typeCharts, setTypeCharts] = useState<TypeChartData[]>([]);
  const fetchData = async () => {
    const data = await obtainUserProfile();
    console.log("User data fetched:", data);
    setUser({
      name: data.name,
      surname: data.surname,
      email: data.email,
      password: "",
      confirmPassword: "",
      idTypeChart: data.typeChart,
    });
    const typeChartsData = await getAllTypeCharts() as TypeChartData[];
    setTypeCharts(typeChartsData);
  };
  
  useEffect(() => {
    fetchData();
  }, []);

  return (
    <DefaultPageLayout>
      <h1 className="page-title" >{t("header.sections.profile.editProfile.title")}</h1>
      <p className="page-description">{t('domains.user.modify.description')}</p>
      <ModifyProfileForm user={user} fetchData={fetchData} typeCharts={typeCharts} />
    </DefaultPageLayout>
  );
}
