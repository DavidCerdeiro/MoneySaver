import { useTranslation } from 'react-i18next';
import { DefaultPageLayout } from "../../shared/layouts/DefaultPageLayout";
import { ModifyProfileForm } from '../components/ModifyProfileForm';
import { useEffect, useState } from 'react';
import { obtainUserProfile } from '../application/UserService';
import type { UserData } from '../schemas/User';
export function ModifyProfilePage() {
  const { t } = useTranslation();
  const [user, setUser] = useState<UserData | null>(null);

  const fetchData = async () => {
    const data = await obtainUserProfile();

    setUser({
      name: data.name,
      surname: data.surname,
      email: data.email,
      password: "",
      confirmPassword: "",
      favouriteGraph: 1,
    });
  };
  
  useEffect(() => {
    fetchData();
  }, []);

  return (
    <DefaultPageLayout>
      <h1 className="page-title" >{t("header.sections.profile.editProfile.title")}</h1>
      <p className="page-description">{t('domains.user.modify.description')}</p>
      <ModifyProfileForm user={user} fetchData={fetchData} />
    </DefaultPageLayout>
  );
}
