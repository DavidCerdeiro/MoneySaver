import { useTranslation } from 'react-i18next';
import { DefaultPageLayout } from "../../shared/layouts/DefaultPageLayout";
import { EditProfileForm } from '../components/EditProfileForm';
import { useEffect, useState } from 'react';
import { getProfile } from '../application/UserService';
import type { UserData } from '../schemas/User';
import type { TypeChartData } from '../../charts/schemas/TypeChart';
import { getAllTypeCharts } from '../../charts/application/ChartService';
export function EditProfilePage() {
  const { t } = useTranslation();
  const [user, setUser] = useState<UserData | null>(null);
  const [typeCharts, setTypeCharts] = useState<TypeChartData[]>([]);
  const fetchData = async () => {
    const data = await getProfile();
    console.log("User data fetched:", data);
    setUser({
      name: data.name,
      surname: data.surname,
      email: data.email,
      password: "",
      confirmPassword: "",
      idTypeChart: data.idTypeChart,
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
      <EditProfileForm user={user} fetchData={fetchData} typeCharts={typeCharts} />
    </DefaultPageLayout>
  );
}
