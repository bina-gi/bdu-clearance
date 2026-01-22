import {
  Building,
  FileCheck,
  Home,
  IdCard,
  Layers,
  Shield,
  Package,
  Users,
  ClipboardList,
} from "lucide-react";

export const navigations = [
  {
    title: "Dashboard",
    href: "/",
    roles: ["ADMIN", "STAFF", "STUDENT", "ADVISOR"],
    orgUnits: null, // null means all org units
    icon: <Home />,
  },

  // Student Pages
  {
    title: "Request Clearance",
    href: "/request-clearance",
    roles: ["STUDENT"],
    orgUnits: null,
    icon: <FileCheck />,
  },
  {
    title: "Report Lost Card",
    href: "/report-lost-card",
    roles: ["STUDENT"],
    orgUnits: null,
    icon: <IdCard />,
  },

  // Staff & Advisor Pages (shared)
  {
    title: "Requested Clearances",
    href: "/requested-clearances",
    roles: ["STAFF", "ADVISOR"],
    orgUnits: null,
    icon: <ClipboardList />,
  },

  // Org-specific pages for STAFF (Library, Cafeteria, etc.)
  {
    title: "Lost Cards",
    href: "/lost-cards",
    roles: ["STAFF"],
    orgUnits: ["LIBRARY", "CAFETERIA"], // Only these org units see this
    icon: <IdCard />,
  },
  {
    title: "Property",
    href: "/property",
    roles: ["STAFF"],
    orgUnits: ["LIBRARY", "CAFETERIA", "REGISTRAR"], // Only these org units see this
    icon: <Package />,
  },

  // Admin Pages
  {
    title: "User Management",
    href: "/user-management",
    roles: ["ADMIN"],
    orgUnits: null,
    icon: <Users />,
  },
  {
    title: "Roles",
    href: "/roles",
    roles: ["ADMIN"],
    orgUnits: null,
    icon: <Shield />,
  },
  {
    title: "Organizational Unit",
    href: "/organizational-unit",
    roles: ["ADMIN"],
    orgUnits: null,
    icon: <Building />,
  },
  {
    title: "Organization Types",
    href: "/organization-types",
    roles: ["ADMIN"],
    orgUnits: null,
    icon: <Layers />,
  },
];

export const getNavigationsForUser = (role, orgUnit) => {
  if (!role) return [];

  return navigations.filter((nav) => {
    const roleAllowed = nav.roles.includes(role);
    if (!roleAllowed) return false;

    if (nav.orgUnits !== null) {
      return orgUnit && nav.orgUnits.includes(orgUnit);
    }

    return true;
  });
};
